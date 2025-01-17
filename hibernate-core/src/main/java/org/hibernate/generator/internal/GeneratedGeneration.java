/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.generator.internal;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.dialect.Dialect;
import org.hibernate.generator.EventType;
import org.hibernate.generator.InDatabaseGenerator;

import java.util.EnumSet;

import static org.hibernate.generator.EventTypeSets.fromArray;
import static org.hibernate.internal.util.StringHelper.isEmpty;

/**
 * A fairly generic {@link InDatabaseGenerator} which marks a property as generated in the
 * database with semantics given explicitly by a {@link Generated @Generated} annotation.
 *
 * @see Generated
 *
 * @author Steve Ebersole
 * @author Gunnar Morling
 */
public class GeneratedGeneration implements InDatabaseGenerator {

	private final EnumSet<EventType> eventTypes;
	private final boolean writable;
	private final String[] sql;

	public GeneratedGeneration(GenerationTime event) {
		eventTypes = event.eventTypes();
		writable = false;
		sql = null;
	}

	public GeneratedGeneration(Generated annotation) {
		eventTypes = annotation.value() == GenerationTime.ALWAYS
				? fromArray( annotation.event() )
				: annotation.value().eventTypes();
		sql = isEmpty( annotation.sql() ) ? null : new String[] { annotation.sql() };
		writable = annotation.writable() || sql != null;
	}

	@Override
	public EnumSet<EventType> getEventTypes() {
		return eventTypes;
	}

	@Override
	public boolean referenceColumnsInSql(Dialect dialect) {
		return writable;
	}

	@Override
	public String[] getReferencedColumnValues(Dialect dialect) {
		return sql;
	}

	@Override
	public boolean writePropertyValue() {
		return writable && sql==null;
	}
}

