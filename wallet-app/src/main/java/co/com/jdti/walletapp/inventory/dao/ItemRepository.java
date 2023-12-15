package co.com.jdti.walletapp.inventory.dao;

import co.com.jdti.walletapp.inventory.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, String> {
}
