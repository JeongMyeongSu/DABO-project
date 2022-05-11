package com.ecommerce.application.impl;

import com.ecommerce.application.IPurchaseService;
import com.ecommerce.domain.exception.ApplicationException;
import com.ecommerce.domain.repository.IItemRepository;
import com.ecommerce.domain.repository.IPurchaseRepository;
import com.ecommerce.domain.repository.entity.Item;
import com.ecommerce.domain.repository.entity.Purchase;
import com.ecommerce.domain.repository.entity.PurchaseInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/*
 */
@Service
public class PurchaseService implements IPurchaseService {

    public static final Logger logger = LoggerFactory.getLogger(ItemService.class);

    private IPurchaseRepository purchaseRepository;
    private IItemRepository itemRepository;

    @Autowired
    public PurchaseService(IPurchaseRepository purchaseRepository,
                           IItemRepository itemRepository) {
        this.purchaseRepository = purchaseRepository;
        this.itemRepository = itemRepository;
    }

    @Override
    public List<Purchase> list() {
        return this.purchaseRepository.list();
    }

    @Override
    public Purchase get(long id) {
        return this.purchaseRepository.get(id);
    }

    @Override
    public Purchase getByPurchaseId(int pid) {
        return this.purchaseRepository.getByPurchaseId(pid);
    }

    private List<PurchaseInfo> getPurchaseInfoList(List<Purchase> purchases) {
        List<PurchaseInfo> purchaseInfoList = new ArrayList<>();
        purchases.forEach(p -> {
            PurchaseInfo purchaseInfo = new PurchaseInfo(p);
            Item item = this.itemRepository.get(p.getItemId());
            purchaseInfo.setItemName(item.getName());
            purchaseInfo.setItemId(item.getId());
            purchaseInfo.setImage(item.getImage());
            purchaseInfoList.add(purchaseInfo);
        });

        return purchaseInfoList;
    }

    /**
     * TODO Sub PJT Ⅲ 과제 3
     * 판매자 관련 Purchase 조회
     */
    @Override
    public List<PurchaseInfo> getBySeller(int id) {
        List<Purchase> purchases = this.purchaseRepository.getBySeller(id);
        if(purchases == null || purchases.size() == 0)
            return null;
        return this.getPurchaseInfoList(purchases);
    }

    /**
     * TODO Sub PJT Ⅲ 과제 3
     * 구매자 관련 Purchase 조회
     * @return
     */
    @Override
    public List<PurchaseInfo> getByBuyer(int id) {
        List<Purchase> purchases = this.purchaseRepository.getByBuyer(id);
        if(purchases == null || purchases.size() == 0)
            return null;
        return this.getPurchaseInfoList(purchases);
    }

    /**
     * TODO Sub PJT Ⅲ 과제 3
     * Purchase 정보 등록
     * @param purchase
     * @return
     */
    @Override
    public Purchase create(final Purchase purchase) {
        if(purchase.getPurchaseId() == 0)
            throw new ApplicationException("purchaseId가 지정되어 있지 않습니다. ");
        if(purchase.getItemId() == 0)
            throw new ApplicationException("구매 상품 정보가 누락되었습니다.");
        if(purchase.getSellerId() == 0 || purchase.getBuyerId() == 0)
            throw new ApplicationException("판매자 혹은 구매자 정보가 누락되었습니다.");
        if(purchase.getContractAddress() == null || purchase.getContractAddress().equals(""))
            throw new ApplicationException("에스크로 컨트랙트 정보가 누락되었습니다.");

        if(purchase.getCreatedAt() == null)
            purchase.setCreatedAt(LocalDateTime.now());

        long id = this.purchaseRepository.create(purchase);

        Purchase purchaseCreated = this.purchaseRepository.get(id);
        if(purchaseCreated == null)
            throw new ApplicationException("구매 정보가 정상 등록되지 않았습니다.");

        return purchaseCreated;
    }

    /**
     * TODO Sub PJT Ⅲ 과제 3
     * Purchase 상태 업데이트
     * @return
     */
    @Override
    public Purchase updateState(int pid, String state) {
        Purchase purchaseStored = this.purchaseRepository.getByPurchaseId(pid);
        if(purchaseStored == null)
            throw new ApplicationException("구매 정보를 찾을 수 없습니다.");
        if(purchaseStored.getState().compareToIgnoreCase(state) == 0)
            throw new ApplicationException("이미 상태가 반영되어 있습니다.");
        if(!isAvailableState(purchaseStored.getState(), state))
            throw new ApplicationException("유효하지 않은 상태가 지정되어 있습니다.");

        purchaseStored.setState(state.toUpperCase());
        long idUpdated = this.purchaseRepository.update(purchaseStored);
        if(idUpdated == 0)
            throw new ApplicationException("구매 정보가 정상 업데이트되지 않았습니다.");

        return this.purchaseRepository.getByPurchaseId(pid);
    }

    private boolean isAvailableState(String current, String next) {
        switch (current) {
            case "I":
                return next.toUpperCase().equals("P") || next.toUpperCase().equals("X");
            case "P":
                return next.toUpperCase().equals("X") || next.toUpperCase().equals("S");
            case "S":
                return next.toUpperCase().equals("C");
            default:
                return false;
        }
    }
}
