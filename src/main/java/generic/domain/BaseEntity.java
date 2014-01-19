package generic.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

@MappedSuperclass
public abstract class BaseEntity implements Comparable<BaseEntity>, Serializable {
	private static final long serialVersionUID = 1L;
	
	protected Long id;
	protected String createdBy;
	protected LocalDateTime createdDate;
	protected Boolean deleted;
	protected String modifiedBy;
	protected LocalDateTime modifiedDate;

	protected BaseEntity() {
	}

	/**
	 * Constructor setting the id
	 * 
	 * @param id
	 */
	protected BaseEntity(final Long id) {
		this.id = id;
	}
	
	@Transient
	public boolean isPersistent() {
		return id != 0;
	}

	@Transient
	public String getIdString() {
		return Long.toString(id);
	}

	public void setId(final Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "[" + id + "]";
	}

	@Override
	public int hashCode() {
		// If it's a new object (id == 0), use the default hashCode, otherwise
		// return the id
		return id == 0 ? super.hashCode() : Long.valueOf(id).hashCode();
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final BaseEntity other = (BaseEntity) obj;
		if (other.id == 0 && id == 0) {
			return false;
		}
		// If one of them is a new object (id == 0) and they aren't the same
		// instance (already checked above),
		// then we consider them not equals. This is coherent with the EJB3
		// implementation.
		return other.id == id;
	}

	@Override
	public int compareTo(final BaseEntity o) {
		return id < o.id ? -1 : (id == o.id ? 0 : 1);
	}
	
	@Transient
	public static String getAlias() {
		return null;
	}

	@Column(name = "created_by", nullable = true, length = 32)
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	@Column(name = "created_date", nullable = true)
	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	@Column(nullable = true)
	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	@Column(name = "modified_by", nullable = true, length = 32)
	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	@Column(name = "modified_date", nullable = true)
	public LocalDateTime getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(LocalDateTime modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
	public void setDefaultValues4AuditFields() {
		LocalDateTime now = new LocalDateTime();
		setCreatedDate( createdDate == null ? now : createdDate );
		setModifiedDate(now);
		setDeleted(false);
		
//		Object object = SecurityContextHolder.getContext().getAuthentication() == null ? null : SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		if ( object instanceof User ) {
//			User user = (User) object;
//			setCreatedBy( createdBy == null ? user.getName() : createdBy );
//			setModifiedBy(user != null ? user.getName() : null);
//			setDeleted(false);
//		}
	}	
}
