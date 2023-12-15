package co.com.jdti.walletapp.wishlist.services;

import co.com.jdti.walletapp.wishlist.dao.IWishRepository;
import co.com.jdti.walletapp.wishlist.model.Wish;
import co.com.jdti.walletapp.wishlist.model.WishDTO;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class WishServiceImpl implements IWishService {

	private final IWishRepository wishRepository;

	@Override
	public WishDTO create(WishDTO wishDTO) {
		if (wishDTO.saved() == null) {
			wishDTO = new WishDTO(wishDTO.id(), wishDTO.description(), wishDTO.price(), new BigDecimal("0.0"));
		}
		Wish wish = toEntity(wishDTO);
		wish.setUserId("1");

		return toDTO(wishRepository.save(wish));
	}

	@Override
	public Optional<WishDTO> findById(String id) {
		return wishRepository.findById(id).map(this::toDTO);
	}

	@Override
	public Page<WishDTO> findAll(int page) {
		PageRequest pageRequest = PageRequest.of(page - 1, 5, Sort.by(Sort.Order.asc("price")));
		return wishRepository.findAll(pageRequest).map(this::toDTO);
	}

	@Override
	public void delete(String id) {
		wishRepository.deleteById(id);
	}

	private WishDTO toDTO(Wish wish) {
		return new WishDTO(wish.getId(), wish.getDescription(), wish.getPrice(), wish.getSaved());
	}

	private Wish toEntity(WishDTO wishDTO) {
		return Wish.builder()
			.id(wishDTO.id())
			.description(wishDTO.description())
			.price(wishDTO.price())
			.saved(wishDTO.saved()).build();
	}
}
