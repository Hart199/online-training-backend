package com.future.onlinetraining.controller;

import com.future.onlinetraining.dto.RatingDTO;
import com.future.onlinetraining.dto.DeleteModuleCategoryDTO;
import com.future.onlinetraining.dto.UpdateModuleCategoryDTO;
import com.future.onlinetraining.dto.UpdateModuleDTO;
import com.future.onlinetraining.entity.Module;
import com.future.onlinetraining.entity.ModuleCategory;
import com.future.onlinetraining.entity.projection.ModuleDetailData;
import com.future.onlinetraining.service.ModuleService;
import com.future.onlinetraining.utility.ResponseHelper;
import com.future.onlinetraining.utility.ValidationHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class ModuleController {

    @Autowired
    private ModuleService moduleService;

    @GetMapping("/modules/_ratings/{id}")
    public ResponseEntity getRatings(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            @PathVariable("id") int id) {

        return new ResponseHelper<>()
                .setSuccessStatus(true)
                .setHttpStatus(HttpStatus.OK)
                .setParam("data", moduleService.getRatings(
                        id, PageRequest.of(page, size)
                ))
                .send();
    }

    @PostMapping("/modules/_ratings/{id}")
    public ResponseEntity addRatings(@PathVariable("id") int id, @Valid @RequestBody RatingDTO ratingDTO, BindingResult bindingResult) {
        ValidationHandler.validate(bindingResult);
        return new ResponseHelper<>()
                .setParam("data", moduleService.addRating(id, ratingDTO))
                .send();
    }

    @GetMapping("/modules/_search")
    public ResponseEntity getRatingBySearchTerms(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "popular", defaultValue = "false") boolean popular,
            @RequestParam(value = "hasExam", required = false) Boolean hasExam){

        Pageable pageable;

        if (!popular)
            pageable = PageRequest.of(page, size);
        else
            pageable = PageRequest.of(page, size);
//            pageable = PageRequest.of(page, size, Sort.by("moduleRatings.value").descending());
//            pageable = PageRequest.of(page, size, JpaSort.unsafe(Sort.Direction.DESC, "m.moduleRatings.value)"));

        return new ResponseHelper<>()
                .setSuccessStatus(true)
                .setHttpStatus(HttpStatus.OK)
                .setParam("data", moduleService.getAllBySearchTerm(pageable, name, category, hasExam))
                .send();
    }

    @GetMapping("/modules/_categories")
    public ResponseEntity getAllModuleCategories
            (@RequestParam(value = "page", defaultValue = "0") int page,
             @RequestParam(value = "size", defaultValue = "5") int size) {
        return new ResponseHelper<>()
                .setSuccessStatus(true)
                .setHttpStatus(HttpStatus.OK)
                .setParam("data", moduleService.getAllModuleCategory(PageRequest.of(page, size)))
                .send();
    }

    @PostMapping("/_trainer/modules/_categories")
    public ResponseEntity addModuleCategory(@Valid @RequestBody ModuleCategory moduleCategory, BindingResult bindingResult) {
        ValidationHandler.validate(bindingResult);
        ModuleCategory category = moduleService.addModuleCategory(moduleCategory);

        return new ResponseHelper<>()
                .setParam("data", category)
                .send();
    }

    @PutMapping("/_trainer/modules/_categories")
    public ResponseEntity updateModuleCategory(
            @Valid @RequestBody UpdateModuleCategoryDTO updateModuleCategoryDTO, BindingResult bindingResult) {
        ValidationHandler.validate(bindingResult);
        ModuleCategory category = moduleService.updateModuleCategory(updateModuleCategoryDTO);

        return new ResponseHelper<>()
                .setParam("data", category)
                .send();
    }

    @DeleteMapping("/_trainer/modules/_categories")
    public ResponseEntity deleteModuleCategory(
            @Valid @RequestBody DeleteModuleCategoryDTO deleteModuleCategoryDTO, BindingResult bindingResult) {
        ValidationHandler.validate(bindingResult);
        boolean category = moduleService.deleteModuleCategory(deleteModuleCategoryDTO);

        if (!category)
            return new ResponseHelper<>()
                    .setSuccessStatus(false)
                    .setHttpStatus(HttpStatus.OK)
                    .setMessage("Kategori tidak ditemukan")
                    .send();

        return new ResponseHelper<>()
                .setMessage("kategori berhasil dihapus.")
                .send();
    }

    @GetMapping("/modules/{id}")
    public ResponseEntity getModuleDetail(@PathVariable("id") Integer id) {
        ModuleDetailData module = moduleService.getModuleDetail(id);

        return new ResponseHelper<>()
                .setParam("data", module)
                .send();
    }

    @PutMapping("/_trainer/modules/{id}")
    public ResponseEntity updateModule(
            @PathVariable("id") Integer id, @RequestBody UpdateModuleDTO updateModuleDTO, BindingResult bindingResult) {
        ValidationHandler.validate(bindingResult);
        Module module = moduleService.editModule(id, updateModuleDTO);

        return new ResponseHelper<>()
                .setParam("data", module)
                .send();
    }

    @DeleteMapping("/_trainer/modules/{id}")
    public ResponseEntity deleteModule(@PathVariable("id") Integer id) {
        boolean module = moduleService.deleteModule(id);

        if (!module)
            return new ResponseHelper<>()
                    .setSuccessStatus(false)
                    .setHttpStatus(HttpStatus.OK)
                    .setMessage("Module tidak ditemukan")
                    .send();

        return new ResponseHelper<>()
                .setMessage("Module berhasil dihapus.")
                .send();
    }
}
