package model;

/**
 * This class provides a single instance of the Appointment class in
 * order to simplify passing data between Views.
 *
 * @author Ike Maldonado
 * @version 1.0
 */
public final class AppointmentSingleton {
    private Appointment appointment;

    private final static AppointmentSingleton INSTANCE = new AppointmentSingleton();

    private AppointmentSingleton() {
    }

    public static AppointmentSingleton getInstance() {
        return INSTANCE;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public Appointment getAppointment() {
        return this.appointment;
    }
}
