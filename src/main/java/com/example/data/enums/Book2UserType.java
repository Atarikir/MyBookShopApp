package com.example.data.enums;

public enum Book2UserType {
  KEPT("Отложена", 1),
  CART("В корзине", 2),
  PAID("Куплена", 3),
  ARCHIVED("В архиве", 4);

  private final String bindingTypeName;
  private final Integer bindingTypeId;

  Book2UserType(String bindingTypeName, Integer bindingTypeId) {
    this.bindingTypeName = bindingTypeName;
    this.bindingTypeId = bindingTypeId;
  }

  public static Integer getBindingTypeId(Book2UserType type) {
    return switch (type) {
      case KEPT -> 1;
      case CART -> 2;
      case PAID -> 3;
      case ARCHIVED -> 4;
    };
  }

  public static String getBindingTypeCodeByTypeID(Integer typeId) {
    return switch (typeId) {
      case 1 -> Book2UserType.KEPT.name();
      case 2 -> Book2UserType.CART.name();
      case 3 -> Book2UserType.PAID.name();
      case 4 -> Book2UserType.ARCHIVED.name();
      default -> "";
    };
  }
}
