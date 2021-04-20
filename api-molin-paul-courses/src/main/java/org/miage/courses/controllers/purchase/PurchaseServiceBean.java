package org.miage.courses.controllers.purchase;

import org.miage.courses.controllers.user.UserService;
import org.miage.courses.entities.card.Card;
import org.miage.courses.entities.course.Course;
import org.miage.courses.entities.purchase.Purchase;
import org.miage.courses.entities.purchase.PurchaseResource;
import org.miage.courses.entities.user.User;
import org.miage.courses.entities.user.UserResource;
import org.miage.courses.requesters.UsersServiceRequester;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PurchaseServiceBean implements PurchaseService{

    private final UserResource userResource;

    private final UsersServiceRequester usersServiceRequester;

    private final PurchaseResource purchaseResource;


    public PurchaseServiceBean(UserResource userResource, UsersServiceRequester usersServiceRequester, PurchaseResource purchaseResource) {
        this.userResource = userResource;
        this.purchaseResource = purchaseResource;
        this.usersServiceRequester = usersServiceRequester;
    }


    @Override
    public Purchase purchase(User user, Course course) {
        Card[] cards = this.usersServiceRequester.getUserCards(user);
        for(Card card : cards) {
            System.out.println(card);
            if(card.getAmount() >= course.getPrice()) {
                card.setAmount(card.getAmount() - course.getPrice());
                this.usersServiceRequester.patchCardAmount(card);
                Purchase purchase = new Purchase();
                purchase.setId(UUID.randomUUID().toString());
                purchase.setCourse(course);
                user.getPurchases().add(purchase);
                this.userResource.save(user);
                return purchase;
            }
        }
        return null;
    }

    @Override
    public Optional<Purchase> find(String id) {
        return this.purchaseResource.findById(id);
    }

    @Override
    public List<Purchase> all() {
        return this.purchaseResource.findAll();
    }
}
