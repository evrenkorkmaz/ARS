package sample.coordinator;

import sample.model.Plane;
import sample.model.Radar;

import java.util.ArrayList;

import static sample.Main.getController;

public class PlaneCoordinator {
    private static ArrayList<Plane> planes = new ArrayList<>();

    private static ArrayList<OnPlaneAddedListener> addListeners = new ArrayList<>();
    private static ArrayList<OnPlaneRemovedListener> removeListeners = new ArrayList<>();

    private static ArrayList<Plane> trackedPlanes = new ArrayList<>();

    public static ArrayList<OnPlaneAddedListener> getAddListeners() {
        return addListeners;
    }

    public static ArrayList<OnPlaneRemovedListener> getRemoveListeners() {
        return removeListeners;
    }

    private static void notifyAddedListeners(Plane plane) {
        for (OnPlaneAddedListener listener : addListeners) {
            listener.onPlaneAdded(plane);
        }
    }

    private static void notifyRemovedListeners(Plane plane) {
        for (OnPlaneRemovedListener listener : removeListeners) {
            listener.onPlaneRemoved(plane);
        }
    }

    public static ArrayList<Plane> getPlanes() {
        return planes;
    }

    public static void addPlane(Plane plane) {
        planes.add(plane);
        notifyAddedListeners(plane);
    }

    public static void removePlane(Plane plane) {
        planes.remove(plane);
        notifyRemovedListeners(plane);
    }

    public static void interceptPlane(Plane target, Radar radar) {
        if (!trackedPlanes.contains(target)) {
            trackedPlanes.add(target);
            addPlane(PlaneFactory.generateInterceptor(target, radar));
        }
    }

    public static void notifyPlaneTakenDown(Plane plane) {
        if (trackedPlanes.contains(plane)) {
            trackedPlanes.remove(plane);
        }
        getController().indicateTakedown(plane.getPosition());
    }

    public interface OnPlaneAddedListener {
        void onPlaneAdded(Plane plane);
    }

    public interface OnPlaneRemovedListener {
        void onPlaneRemoved(Plane plane);
    }

}
