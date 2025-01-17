/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.annotations;

import org.hibernate.CacheMode;
import org.hibernate.Remove;

/**
 * Enumerates the different interaction modes between the session
 * and the second-level Cache. This enumeration is isomorphic to
 * {@link CacheMode} and exists only for historical reasons.
 *
 * @author Emmanuel Bernard
 * @author Carlos Gonzalez-Cadenas
 *
 * @deprecated use {@link CacheMode} or
 *             {@link jakarta.persistence.CacheStoreMode} and
 *             {@link jakarta.persistence.CacheRetrieveMode}.
 */
@Deprecated(since = "6.2") @Remove
public enum CacheModeType {
	/**
	 * Corresponds to {@link CacheMode#GET}.
	 *
	 * @see CacheMode#GET
	 */
	GET( CacheMode.GET ),

	/**
	 * Corresponds to {@link CacheMode#IGNORE}.
	 *
	 * @see CacheMode#IGNORE
	 */
	IGNORE( CacheMode.IGNORE ),

	/**
	 * Corresponds to {@link CacheMode#NORMAL}.
	 *
	 * @see CacheMode#NORMAL
	 */
	NORMAL( CacheMode.NORMAL ),

	/**
	 * Corresponds to {@link CacheMode#PUT}.
	 *
	 * @see CacheMode#PUT
	 */
	PUT( CacheMode.PUT ),

	/**
	 * Corresponds to {@link CacheMode#REFRESH}.
	 *
	 * @see CacheMode#REFRESH
	 */
	REFRESH( CacheMode.REFRESH );

	private final CacheMode cacheMode;

	CacheModeType(CacheMode cacheMode) {
		this.cacheMode = cacheMode;
	}

	public CacheMode getCacheMode() {
		return cacheMode;
	}

	/**
	 * Conversion from {@link CacheMode} to {@link CacheModeType}.
	 *
	 * @param cacheMode The cache mode to convert
	 *
	 * @return The corresponding enum value.  Will be {@code null} if the given {@code accessType} is {@code null}.
	 */
	public static CacheModeType fromCacheMode(CacheMode cacheMode) {
		if ( null == cacheMode ) {
			return null;
		}

		switch ( cacheMode ) {
			case NORMAL: {
				return NORMAL;
			}
			case GET: {
				return GET;
			}
			case PUT: {
				return PUT;
			}
			case REFRESH: {
				return REFRESH;
			}
			case IGNORE: {
				return IGNORE;
			}
			default: {
				throw new IllegalArgumentException( "Unrecognized CacheMode : " + cacheMode );
			}
		}
	}
}
