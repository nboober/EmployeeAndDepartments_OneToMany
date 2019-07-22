package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class HomeController {

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @RequestMapping("/")
    public String homePage(Model model){
        model.addAttribute("departments", departmentRepository.findAll());
        model.addAttribute("employees", employeeRepository.findAll());
        return "homePage";
    }

    @GetMapping("/addEmployee")
    public String employeeForm(Model model){
        model.addAttribute("employee", new Employee());
        model.addAttribute("departments", departmentRepository.findAll());

        return "employeeForm";
    }

    @GetMapping("/addDepartment")
    public String departmentForm(Model model){
        model.addAttribute("department", new Department());
        return "departmentForm";
    }

    @PostMapping("/processEmployee")
    public String processEmployee(@Valid Employee employee,BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("departments", departmentRepository.findAll());
            return "employeeForm";
        }
        employeeRepository.save(employee);
        return "redirect:/";
    }

    @PostMapping("/processDepartment")
    public String processDepartment(@Valid Department department, BindingResult result){
        if (result.hasErrors()){
            return "departmentForm";
        }
        departmentRepository.save(department);
        return "redirect:/";
    }

    @RequestMapping("/updateEmployee/{id}")
    public String updateEmployee(@PathVariable("id") long id, Model model){
        model.addAttribute("employee", employeeRepository.findById(id).get());
        model.addAttribute("departments", departmentRepository.findAll());
        return "employeeForm";
    }

    @RequestMapping("/updateDepartment/{id}")
    public String updateDepartment(@PathVariable("id") long id, Model model){
        model.addAttribute("department", departmentRepository.findById(id).get());
        return "departmentForm";
    }

    @RequestMapping("/deleteEmployee/{id}")
    public String deleteEmployee(@PathVariable("id") long id){
        employeeRepository.deleteById(id);
        return "redirect:/";
    }

    @RequestMapping("/deleteDepartment/{id}")
    public String deleteDepartment(@PathVariable("id") long id){
        departmentRepository.deleteById(id);
        return "redirect:/";
    }

}
