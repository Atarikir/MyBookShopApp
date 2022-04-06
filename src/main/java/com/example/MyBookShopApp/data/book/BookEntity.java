package com.example.MyBookShopApp.data.book;

import com.example.MyBookShopApp.data.author.AuthorEntity;
import com.example.MyBookShopApp.data.genre.GenreEntity;
import com.example.MyBookShopApp.data.user.UserEntity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "book")
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "pub_date", columnDefinition = "DATE NOT NULL")
    private LocalDate pubDate;

    @Column(name = "is_bestseller", columnDefinition = "SMALLINT NOT NULL")
    private short isBestseller;

    @Column(columnDefinition = "VARCHAR(255) NOT NULL")
    private String slug;

    @Column(columnDefinition = "VARCHAR(255) NOT NULL")
    private String title;

    @Column(columnDefinition = "VARCHAR(255) NOT NULL")
    private String image;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "INT NOT NULL")
    private int price;

    @Column(columnDefinition = "SMALLINT NOT NULL DEFAULT 0")
    private short discount;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "book2author",
            joinColumns = {@JoinColumn(name = "book_id")},
            inverseJoinColumns = {@JoinColumn(name = "author_id")}
    )
    private List<AuthorEntity> authorEntityList;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "book2genre",
            joinColumns = {@JoinColumn(name = "book_id")},
            inverseJoinColumns = {@JoinColumn(name = "genre_id")}
    )
    private List<GenreEntity> genreEntityList;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "book2user",
            joinColumns = {@JoinColumn(name = "book_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    private List<UserEntity> userEntityList;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "file_download",
            joinColumns = {@JoinColumn(name = "book_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    private List<UserEntity> userEntities;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "balance_transaction",
            joinColumns = {@JoinColumn(name = "book_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    private List<UserEntity> userTransactList;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "book_review",
            joinColumns = {@JoinColumn(name = "book_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    private List<UserEntity> userReviewList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getPubDate() {
        return pubDate;
    }

    public void setPubDate(LocalDate pubDate) {
        this.pubDate = pubDate;
    }

    public short getIsBestseller() {
        return isBestseller;
    }

    public void setIsBestseller(short isBestseller) {
        this.isBestseller = isBestseller;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public short getDiscount() {
        return discount;
    }

    public void setDiscount(short discount) {
        this.discount = discount;
    }

    public List<AuthorEntity> getAuthorEntityList() {
        return authorEntityList;
    }

    public void setAuthorEntityList(List<AuthorEntity> authorEntityList) {
        this.authorEntityList = authorEntityList;
    }

    public List<GenreEntity> getGenreEntityList() {
        return genreEntityList;
    }

    public void setGenreEntityList(List<GenreEntity> genreEntityList) {
        this.genreEntityList = genreEntityList;
    }

    public List<UserEntity> getUserEntityList() {
        return userEntityList;
    }

    public void setUserEntityList(List<UserEntity> userEntityList) {
        this.userEntityList = userEntityList;
    }

    public List<UserEntity> getUserEntities() {
        return userEntities;
    }

    public void setUserEntities(List<UserEntity> userEntities) {
        this.userEntities = userEntities;
    }

    public List<UserEntity> getUserTransactList() {
        return userTransactList;
    }

    public void setUserTransactList(List<UserEntity> userTransactList) {
        this.userTransactList = userTransactList;
    }

    public List<UserEntity> getUserReviewList() {
        return userReviewList;
    }

    public void setUserReviewList(List<UserEntity> userReviewList) {
        this.userReviewList = userReviewList;
    }
}
