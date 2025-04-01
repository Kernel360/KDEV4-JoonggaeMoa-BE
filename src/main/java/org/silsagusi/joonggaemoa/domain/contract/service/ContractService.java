package org.silsagusi.joonggaemoa.domain.contract.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.silsagusi.joonggaemoa.domain.contract.entity.Contract;
import org.silsagusi.joonggaemoa.domain.contract.repository.ContractRepository;
import org.silsagusi.joonggaemoa.domain.contract.service.command.ContractCommand;
import org.silsagusi.joonggaemoa.domain.customer.entity.Customer;
import org.silsagusi.joonggaemoa.domain.customer.repository.CustomerRepository;
import org.silsagusi.joonggaemoa.global.api.exception.CustomException;
import org.silsagusi.joonggaemoa.global.api.exception.ErrorCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class ContractService {

    private final ContractRepository contractRepository;
    private final CustomerRepository customerRepository;
    private final AmazonS3 amazonS3;

    public void createContract(
            Long landlordId,
            Long tenantId,
            LocalDate createdAt,
            LocalDate expiredAt,
            MultipartFile file
    ) throws IOException {

        Customer customerLandlord = customerRepository.findById(landlordId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ELEMENT));
        Customer customerTenant = customerRepository.findById(tenantId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ELEMENT));

        String filename = fileUpload(file);

        Contract contract = new Contract(
                customerLandlord,
                customerTenant,
                createdAt,
                expiredAt,
                filename
        );
        contractRepository.save(contract);
    }

    public Page<ContractCommand> getAllContracts(Pageable pageable) {
        Page<Contract> contractPage = contractRepository.findAll(pageable);
        Page<ContractCommand> contractCommandPage = contractPage.map(ContractCommand::of);
        Page<ContractCommand> s3ContractCommandPage = contractCommandPage.map(
                it -> {
                    try {
                        it.setUrl(getUrl(it.getUrl()));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    return it;
                });
        return s3ContractCommandPage;
    }

    public ContractCommand getContractById(Long contractId) throws IOException {
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ELEMENT));
        ContractCommand command = ContractCommand.of(contract);
        command.setUrl(getUrl(command.getUrl()));
        return command;
    }

    public void deleteContract(Long contractId) {
        contractRepository.deleteById(contractId);
    }

    private String fileUpload(MultipartFile file) throws IOException {
        String filename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

        //meta data
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(file.getContentType());
        objectMetadata.setContentLength(file.getSize());

        //S3 upload
        PutObjectRequest putObjectRequest = new PutObjectRequest("joonggaemoa", filename, file.getInputStream(),
                objectMetadata);
        amazonS3.putObject(putObjectRequest);

        return filename;
    }

    public String getUrl(String fileName) throws IOException {
        return amazonS3.getUrl("joonggaemoa", fileName).toString();
    }

}
