package model;

/**
 * This class is used to provide a single instance an Appointment object
 * in order to simplify passing data between controllers.
 *
 * @author Ike Maldonado
 * @version 1.0
 */
public final class AppointmentSingleton {
    private Appointment appointment;

    // Instantiate the singleton
    private final static AppointmentSingleton INSTANCE = new AppointmentSingleton();

    private AppointmentSingleton() {
    }

    /**
     * Returns the instance of the AppointmentSingleton object. Must be
     * called in order to set and get the Appointment object.
     *
     * @return Returns an instance of the AppointmentSingleton object.
     */
    public static AppointmentSingleton getInstance() {
        return INSTANCE;
    }


    /**
     * Sets the Appointment object.
     *
     * @param appointment An Appointment object
     */
    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }


    /**
     * Gets the Appointment object.
     *
     * @return An Appointment object
     */
    public Appointment getAppointment() {
        return this.appointment;
    }
}
