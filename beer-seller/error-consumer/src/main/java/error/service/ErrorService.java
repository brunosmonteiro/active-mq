package error.service;

import error.entity.ErrorMessage;
import error.repository.ErrorMessageRepository;
import org.springframework.stereotype.Service;

@Service
public record ErrorService(ErrorMessageRepository errorMessageRepository) {
    public void storeMessage(final String message) {
        errorMessageRepository.save(new ErrorMessage(message));
    }
}
