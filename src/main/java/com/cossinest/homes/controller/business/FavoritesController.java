package com.cossinest.homes.controller.business;

import com.cossinest.homes.domain.concretes.business.Favorites;
import com.cossinest.homes.payload.request.business.AdvertRequest;
import com.cossinest.homes.payload.response.ResponseMessage;
import com.cossinest.homes.payload.response.business.AdvertResponse;
import com.cossinest.homes.service.business.FavoritesService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/favorites") // http://localhost:8080/favorites
@RequiredArgsConstructor
public class FavoritesController {

    private final FavoritesService favoritesService;

    @GetMapping("/auth") //http:localhost:8080/favorites/auth
    //@PreAuthorize("hasAnyAuthority('CUSTOMER')")
    public ResponseEntity<List<AdvertResponse>> getAuthenticatedUsersFavorites(HttpServletRequest request) {

        List<AdvertResponse> favorites = favoritesService.getAuthenticatedUsersFavorites(request);

        return ResponseEntity.ok(favorites);
    }


    @GetMapping("/admin/{id}") //http://localhost:8080/favorites/admin/23
    //@PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public ResponseEntity<List<AdvertResponse>> getUsersFavorites(@PathVariable("id") Long id) {

        return ResponseEntity.ok(favoritesService.getUsersFavorites(id));

    }


    @PostMapping("/{id}/auth")
    //@PreAuthorize("hasAnyAuthority('CUSTOMER')")
    public ResponseEntity<ResponseMessage<List<Favorites>>> addAndRemoveAuthenticatedUserFavorites(HttpServletRequest httpServletRequest,
                                                                                                   @RequestBody @Valid AdvertRequest advertRequest,
                                                                                                   @PathVariable Long id) {
        ResponseMessage<List<Favorites>> advertResponse = favoritesService.addAndRemoveAuthenticatedUserFavorites(httpServletRequest, advertRequest, id);

        return ResponseEntity.ok(advertResponse);
    }



    @DeleteMapping("/auth") // http://localhost:8080/favorites/auth + DELETE
    //@PreAuthorize("hasAnyAuthority('CUSTOMER')")
    public ResponseMessage removeAllFavoritesofAuthenticatedUser( HttpServletRequest httpServletRequest, @PathVariable Long id) {
        return favoritesService.removeAllFavoritesofAuthenticatedUser(id);
    }



    @DeleteMapping("/admin") // http://localhost:8080/favorites/admin + DELETE
    //@PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public ResponseMessage removeAllFavoritesOfAUser(@PathVariable Long id) {
        return favoritesService.removeAllFavoritesOfAUser(id);
    }




    @DeleteMapping("/{id}/admin") // http://localhost:8080/favorites/{id}/admin + DELETE
    //@PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public ResponseMessage removeFavoriteByIdForAdmin(@PathVariable Long id) {
        return favoritesService.removeFavoriteByIdForAdmin(id);
    }


}
