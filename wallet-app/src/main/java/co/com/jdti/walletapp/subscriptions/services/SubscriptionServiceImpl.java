package co.com.jdti.walletapp.subscriptions.services;

import co.com.jdti.walletapp.subscriptions.dao.ISubscriptionRepository;
import co.com.jdti.walletapp.subscriptions.model.Subscription;
import co.com.jdti.walletapp.subscriptions.model.SubscriptionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements ISubscriptionService {

	private final ISubscriptionRepository iSubscriptionRepository;

	@Override
	public SubscriptionDTO create(SubscriptionDTO subscriptionDTO) {
		return convertToDTO(iSubscriptionRepository.save(convertToEntity(subscriptionDTO)));
	}

	@Override
	public Optional<SubscriptionDTO> findById(String idSubscription) {
		return iSubscriptionRepository.findById(idSubscription).map(this::convertToDTO);
	}

	@Override
	public Page<SubscriptionDTO> findAll(int page) {
		PageRequest pageRequest = PageRequest.of(page - 1, 5, Sort.by(Sort.Order.asc("description")));
		return iSubscriptionRepository.findAll(pageRequest)
			.map(this::convertToDTO);
	}

	@Override
	public void delete(String idSubscription) {
		iSubscriptionRepository.deleteById(idSubscription);
	}

	private SubscriptionDTO convertToDTO(Subscription subscription) {
		return SubscriptionDTO.builder()
			.id(subscription.getId())
			.description(subscription.getDescription())
			.price(subscription.getPrice())
			.date(subscription.getDate())
			.image(subscription.getImage())
			.url(subscription.getUrl())
			.build();
	}

	private Subscription convertToEntity(SubscriptionDTO subscriptionDTO) {
		return Subscription.builder()
			.id(subscriptionDTO.getId())
			.description(subscriptionDTO.getDescription())
			.price(subscriptionDTO.getPrice())
			.date(subscriptionDTO.getDate())
			.image(subscriptionDTO.getImage())
			.url(subscriptionDTO.getUrl())
			.build();
	}
}
