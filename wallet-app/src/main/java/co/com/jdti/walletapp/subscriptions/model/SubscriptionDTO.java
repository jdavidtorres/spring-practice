package co.com.jdti.walletapp.subscriptions.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionDTO {

	private String id;
	private String description;
	private String price;
	private Date date;
	private String image;
	private String url;
}
