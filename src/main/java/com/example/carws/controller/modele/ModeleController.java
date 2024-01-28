package com.example.carws.controller.modele;

import com.example.carws.model.primaire.Categorie;
import com.example.carws.model.primaire.Marque;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import com.example.carws.model.primaire.Modele;
import com.example.carws.model.primaire.Moteur;
import com.example.carws.request.ModeleRequest;
import com.example.carws.service.ModeleService;

import com.example.carws.response.*;
import com.example.carws.service.MarqueService;
import com.example.carws.service.model.ModelCategorieService;
import java.util.List;

@RestController
@RequestMapping("/api/modeles")
public class ModeleController{

	@Autowired ModeleService modeleService;
          @Autowired ModelCategorieService modeleCategorieService;
          @Autowired MarqueService marqueService;
          
	@GetMapping
	public ResponseEntity<?> getModeles() throws Exception{
		Response r = new Response();
              try{
			Modele[] modeles = null;
			modeles = modeleService.getAllModeles().toArray( new Modele[0] );
                           List<Marque> marques = marqueService.getAllMarques();
                           r.addData("modeles" , modeles);
                           r.addData("marques" , marques);
			return ResponseEntity.status( HttpStatus.OK ).body( r );
		}catch( Exception exception ){
			exception.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body( exception.getMessage() );
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Response> getModele( @PathVariable("id") String id ) throws Exception{
		try{
			Modele modele = modeleService.getModele(id);
			Response response = new Response();
			response.addData( "items" , modele );
			return ResponseEntity.status(HttpStatus.OK).body( response );
		}catch(Exception e){
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body( new Response().addError( "exception", e.getMessage() ) );
		}
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<Response> addModele( @RequestBody ModeleRequest modele ) throws Exception{
		Response response = new Response();
		try{
			modeleService.saveModele( modele.toModele() );
			response.addMessage("save", "Le modele a ete ajouter");
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}catch(Exception e){
			response.addError("error" , e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<Response> updateModele( @RequestBody ModeleRequest modele, @PathVariable("id") String id ){
		Response response = new Response();
		try{
                           Modele m = modele.toModele();
			m.setId(id);
			modeleService.updateModele( m );
			return ResponseEntity.status( HttpStatus.OK ).body( response.addMessage( "success" , "modele mis a jour" ) );
		}catch (Exception e) {
			response.addError("error" , e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body( response );
		}
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Response> deleteModele( @PathVariable("id") String id ){
		Response response = new Response();
		try{
			modeleService.deleteModele( id );
			return ResponseEntity.status( HttpStatus.OK ).body( response.addMessage( "success" , "Modele supprimé" ) );
		}catch (Exception e) {
			response.addError("error" , e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body( response );
		}
	}
    
//         @PostMapping( "/categories/{categorie}" )
//         public ResponseEntity<Response> addModelToCategory(@RequestBody Modele modele, @PathVariable("categorie") Integer categorie ){
//                   Response response = new Response();
//                   try{
//                             modeleCategorieService.addModelToCategory(modele.getId(), categorie);
//                             return ResponseEntity.status( HttpStatus.OK ).body( response.addMessage( "success" , "Modele ajouté à la catégorie" ) );
//                   }catch( Exception e ){
//                              response.addError("error" , e.getMessage());
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body( response );
//                   }
//                       
//         }
         
		@PreAuthorize("hasRole('ADMIN')")
          @PostMapping( "/{id}/categories/{categorie}" )
          public ResponseEntity<?> addCategory( @PathVariable String id, @PathVariable String categorie ){
                    Response response = new Response();
                    try{
                              this.modeleService.addCategoryToModel(id, categorie);
                              
                              return ResponseEntity.ok().body( response.addMessage( "success", "Catégoie ajouté au modele" ) );
                              
                    }catch(Exception e){
                              return ResponseEntity.badRequest().body(response.addError("error" , e.getMessage()));
                    }
          }
          
		@PreAuthorize("hasRole('ADMIN')")
          @PostMapping("/{id}/moteurs/{moteur}")
          public ResponseEntity<?> addEngine( @PathVariable String id, @PathVariable String moteur ){
                    Response response = new Response();
                    try{
                              this.modeleService.addEngineToModel(id, moteur);
                              
                              return ResponseEntity.ok().body( response.addMessage( "success", "Moteur ajouté au modele" ) );
                              
                    }catch(Exception e){
                              return ResponseEntity.badRequest().body(response.addError("error" , e.getMessage()));
                    }
          }

}