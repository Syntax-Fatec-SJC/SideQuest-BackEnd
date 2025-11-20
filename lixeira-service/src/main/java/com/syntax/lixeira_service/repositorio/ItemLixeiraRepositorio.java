package com.syntax.lixeira_service.repositorio;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.syntax.lixeira_service.modelo.ItemLixeira;
import com.syntax.lixeira_service.modelo.ItemLixeira.StatusLixeira;
import com.syntax.lixeira_service.modelo.ItemLixeira.TipoItem;

@Repository
public interface ItemLixeiraRepositorio extends MongoRepository<ItemLixeira, String> {

    List<ItemLixeira> findByStatus(StatusLixeira status);

    List<ItemLixeira> findByTipoItemAndStatus(TipoItem tipoItem, StatusLixeira status);

    Optional<ItemLixeira> findByItemIdAndTipoItemAndStatus(
            String itemId, TipoItem tipoItem, StatusLixeira status
    );

    List<ItemLixeira> findByDeletadoPorAndStatus(String deletadoPor, StatusLixeira status);

    List<ItemLixeira> findByDeletadoEmBeforeAndStatus(Date data, StatusLixeira status);

    long countByStatus(StatusLixeira status);

    long countByTipoItemAndStatus(TipoItem tipoItem, StatusLixeira status);
}
