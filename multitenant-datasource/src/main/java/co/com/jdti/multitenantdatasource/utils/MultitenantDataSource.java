package co.com.jdti.multitenantdatasource.utils;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.sql.DataSource;
import java.util.concurrent.atomic.AtomicBoolean;

public class MultitenantDataSource extends AbstractRoutingDataSource {
	private final AtomicBoolean initialized = new AtomicBoolean();

	@Override
	protected DataSource determineTargetDataSource() {
		if (this.initialized.compareAndSet(false, true)) {
			this.afterPropertiesSet();
		}
		return super.determineTargetDataSource();
	}

	@Override
	protected Object determineCurrentLookupKey() {
		var authentication = SecurityContextHolder
			.getContext()
			.getAuthentication();
		if (authentication != null && authentication.getPrincipal() instanceof MultitenantUser user) {
			var tenantId = user.getTenantId();
			System.out.println("the tenantId is " + tenantId);
			return tenantId;
		}
		return null;
	}
}
