package co.com.jdti.walletapp.wishlist.dao;

import co.com.jdti.walletapp.wishlist.model.Wish;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IWishRepository extends JpaRepository<Wish, String> {
}
