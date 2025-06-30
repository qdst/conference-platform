package com.conference.platform.control.util;

public final class Utils {

  public static int computeAvailableCapacity(int maxCapacity, int activeRegistration) {
    return maxCapacity - activeRegistration;
  }

  public static boolean hasFreeCapacity(int maxCapacity, int activeRegistration) {
    return maxCapacity > activeRegistration;
  }
  public static boolean hasNoCapacity(int maxCapacity, int activeRegistration) {
    return !hasFreeCapacity(maxCapacity, activeRegistration);
  }
}
