package org.byovsiannikov.sticky_notes.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JWTLoginDTO {
    private String userName;
    private String password;


//
//    private final CartRepository cartRepository;
//
//    private final RecipeRepository recipeRepository;
//
//    private final CartConverter cartConverter;
//
//    private final UserService userService;
//
//    private void checkCartOwnership(Long cartId) {
//        User user = userService.getCurrentUser();
//        Optional<CartDTO> cartOptional = cartRepository.findById(cartId);
//        if (cartOptional.isPresent()) {
//            CartDTO cartDTO = cartOptional.get();
//            if (!cartDTO.getUserUid().equals(user.getUid())) {
//                throw new AccessDeniedException("User does not have permission to operate with this cart");
//            }
//        } else {
//            throw new IllegalArgumentException("Invalid cart ID");
//        }
//    }
//
//    public Cart addToCart(Long recipeId, Integer amount) {
//        User user = userService.getCurrentUser();
//        Optional<RecipeDTO> recipeOptional = recipeRepository.findById(recipeId);
//
//        if (recipeOptional.isPresent()) {
//            RecipeDTO recipeDTO = recipeOptional.get();
//
//            Optional<CartDTO> cartOptional = cartRepository.findByUserUidAndRecipe(user.getUid(), recipeDTO);
//            CartDTO cartDTO;
//            if (cartOptional.isPresent()) {
//                cartDTO = cartOptional.get();
//                cartDTO.setAmount(cartDTO.getAmount() + amount);
//            } else {
//                cartDTO = CartDTO.builder()
//                        .userUid(user.getUid())
//                        .recipe(recipeDTO)
//                        .amount(amount)
//                        .build();
//            }
//            cartDTO = cartRepository.save(cartDTO);
//            return cartConverter.fromDTO(cartDTO);
//        } else {
//            throw new IllegalArgumentException("Invalid customer or recipe ID");
//        }
//    }
//}
}
