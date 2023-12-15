package co.com.jdti.walletapp.subscriptions.dao;

import co.com.jdti.walletapp.subscriptions.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISubscriptionRepository extends JpaRepository<Subscription, String> {
}
