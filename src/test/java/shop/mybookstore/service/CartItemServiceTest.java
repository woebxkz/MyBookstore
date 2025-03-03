package shop.mybookstore.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import shop.mybookstore.repository.CartItemRepository;

@ExtendWith(MockitoExtension.class)
public class CartItemServiceTest {

    @Mock
    CartItemRepository cartItemRepository;

    @InjectMocks
    CartItemService cartItemService;

    @Test
    void addBookToCartTest_Success() {
    }

}
