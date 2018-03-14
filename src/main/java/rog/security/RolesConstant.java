package rog.security;

public enum  RolesConstant {

    ADMIN(1), USER(2), ADMIN_PLANU(3), USER_PLANU(4), ADMIN_GLOBAL(5);

    private long id;

    RolesConstant(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
