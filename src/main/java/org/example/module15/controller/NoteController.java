package org.example.module15.controller;


import lombok.RequiredArgsConstructor;
import org.example.module15.dao.model.Note;
import org.example.module15.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@RequiredArgsConstructor
@Controller
@RequestMapping("/note")
public class NoteController {
    @Autowired
    private final NoteService noteService;

    @GetMapping("/addNote")
    public String addNote() {
        Note newNote = new Note();
        newNote.setTitle("New Note");
        newNote.setContent("New Content");
        noteService.add(newNote);
        return "redirect:/note/list";
    }

    @GetMapping("/list")
    public ModelAndView list() {
        ModelAndView modelAndView = new ModelAndView("note/noteList");
        modelAndView.addObject("notes", noteService.listAll());
        return modelAndView;
    }

    @PostMapping("/delete")
    public String delete(@RequestParam(name = "id") Long id) {
        noteService.deleteById(id);
        return "redirect:/note/list";
    }

    @GetMapping("/edit")
    public String editPage(Model model, @RequestParam(name = "id") Long id) {
        Note note = noteService.getById(id);
        model.addAttribute("note", note);
        return "note/noteEdit";
    }

    @PostMapping("/edit")
    public RedirectView edit(@ModelAttribute("note") Note note) {
        RedirectView redirectView = new RedirectView("/note/list");
        noteService.update(note);
        return redirectView;
    }
}
