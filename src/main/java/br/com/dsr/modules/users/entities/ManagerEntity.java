package br.com.dsr.modules.users.entities;

import java.util.UUID;

import br.com.dsr.modules.stores.entities.StoreEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity(name = "managers")
@EqualsAndHashCode(callSuper = true)
public class ManagerEntity extends UserEntity {

    @ManyToOne()
    @JoinColumn(name = "store_id", insertable = false, updatable = false)
    private StoreEntity storeEntity;

    @Column(name = "store_id")
    private UUID storeId;
}
