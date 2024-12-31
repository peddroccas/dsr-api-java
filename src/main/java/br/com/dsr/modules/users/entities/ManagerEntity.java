package br.com.dsr.modules.users.entities;

import br.com.dsr.modules.stores.entities.StoreEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity(name = "managers")
@EqualsAndHashCode(callSuper = true)
public class ManagerEntity extends UserEntity {

    @OneToOne()
    @JoinColumn(name = "store_id", insertable = false, updatable = false)
    private StoreEntity storeEntity;
}
