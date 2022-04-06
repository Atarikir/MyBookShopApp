package com.example.MyBookShopApp.data.user;

import com.example.MyBookShopApp.data.book.BookEntity;
import com.example.MyBookShopApp.data.book.review.BookReviewLikeEntity;
import com.example.MyBookShopApp.data.book.review.MessageEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(columnDefinition = "VARCHAR(255) NOT NULL")
    private String hash;

    @Column(name = "reg_time", columnDefinition = "TIMESTAMP NOT NULL")
    private LocalDateTime regTime;

    @Column(columnDefinition = "INT NOT NULL")
    private int balance;

    @Column(columnDefinition = "VARCHAR(255) NOT NULL")
    private String name;

    @OneToOne(mappedBy = "userId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private UserContactEntity userContact;

    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BookReviewLikeEntity> likeList;

    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MessageEntity> messageList;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "book2user",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "book_id")}
    )
    private List<BookEntity> bookEntityList;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "file_download",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "book_id")}
    )
    private List<BookEntity> bookEntities;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "balance_transaction",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "book_id")}
    )
    private List<BookEntity> bookTransactList;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "book_review",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "book_id")}
    )
    private List<BookEntity> bookReviewList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public LocalDateTime getRegTime() {
        return regTime;
    }

    public void setRegTime(LocalDateTime regTime) {
        this.regTime = regTime;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BookEntity> getBookEntityList() {
        return bookEntityList;
    }

    public void setBookEntityList(List<BookEntity> bookEntityList) {
        this.bookEntityList = bookEntityList;
    }

    public List<BookEntity> getBookEntities() {
        return bookEntities;
    }

    public void setBookEntities(List<BookEntity> bookEntities) {
        this.bookEntities = bookEntities;
    }

    public List<BookEntity> getBookTransactList() {
        return bookTransactList;
    }

    public void setBookTransactList(List<BookEntity> bookTransactList) {
        this.bookTransactList = bookTransactList;
    }

    public List<BookEntity> getBookReviewList() {
        return bookReviewList;
    }

    public void setBookReviewList(List<BookEntity> bookReviewList) {
        this.bookReviewList = bookReviewList;
    }

    public List<BookReviewLikeEntity> getLikeList() {
        return likeList;
    }

    public void setLikeList(List<BookReviewLikeEntity> likeList) {
        this.likeList = likeList;
    }

    public List<MessageEntity> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<MessageEntity> messageList) {
        this.messageList = messageList;
    }

    public UserContactEntity getUserContact() {
        return userContact;
    }

    public void setUserContact(UserContactEntity userContact) {
        this.userContact = userContact;
    }
}
