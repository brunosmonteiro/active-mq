package error.repository;

import error.entity.ErrorMessage;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ErrorMessageRepository extends ElasticsearchRepository<ErrorMessage, String> {
}
