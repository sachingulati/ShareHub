package sharehub

class PasswordToken {
    String token
    static belongsTo = [user: User]
    static constraints = {
        user unique: true
        token blank: false, unique: true
    }
}
