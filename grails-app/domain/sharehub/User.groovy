package sharehub

class User {

	transient springSecurityService

	String username
	String password
    String firstName
    String lastName
    String email
    String photoUrl
    Date dateCreated
    Date lastUpdated
    static hasMany = [topics: Topic, resources: Resource, subscriptions: Subscription, resourceStatus: ResourceStatus, invites: Invite]
    static hasOne = [passwordToken: PasswordToken]
    boolean enabled = true
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired

	static transients = ['springSecurityService', 'name']

    static constraints = {
        username unique: true, blank: false
        photoUrl nullable: true
        email unique: true, email: true, blank: false
        firstName blank: false, nullable: false
        lastName blank: false, nullable: false
        password blank: false, minSize: 8
        passwordToken nullable: true
    }

	static mapping = {
		password column: '`password`'
	}

	Set<Role> getAuthorities() {
		UserRole.findAllByUser(this).collect { it.role }
	}

    def getSubscriptionCount() {
        return subscriptions.size()
    }

    def getTopicCount() {
        return topics.size()
    }

    String getName() {
        return firstName + " " + lastName
    }

	def beforeInsert() {
		encodePassword()
	}

	def beforeUpdate() {
		if (isDirty('password')) {
			encodePassword()
		}
	}

	protected void encodePassword() {
		password = springSecurityService?.passwordEncoder ? springSecurityService.encodePassword(password) : password
	}
}
