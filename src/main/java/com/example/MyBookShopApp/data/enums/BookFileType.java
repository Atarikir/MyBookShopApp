package com.example.MyBookShopApp.data.enums;

public enum BookFileType {
  PDF(".pdf"),
  EPUB(".epub"),
  FB2(".fb2");

  private final String fileExtensionString;

  BookFileType(String fileExtensionString) {
    this.fileExtensionString = fileExtensionString;
  }

  public static String getExtensionStringByTypeID(Integer typeId) {
    return switch (typeId) {
      case 1 -> BookFileType.PDF.fileExtensionString;
      case 2 -> BookFileType.EPUB.fileExtensionString;
      case 3 -> BookFileType.FB2.fileExtensionString;
      default -> "";
    };
  }
}

