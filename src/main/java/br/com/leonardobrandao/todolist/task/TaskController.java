package br.com.leonardobrandao.todolist.task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.leonardobrandao.todolist.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/task")
public class TaskController {
    
    @Autowired
    private ITaskRepository taskRepository;

    @PostMapping("/")
    public ResponseEntity create(@RequestBody TaskModel taskModel, HttpServletRequest request) { 
        taskModel.setIdUser((UUID) request.getAttribute("idUser"));
        
        var currentDate = LocalDateTime.now();
        if (currentDate.isAfter(taskModel.getStartsAt()) || currentDate.isAfter(taskModel.getEndsAt())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                   .body("As datas de início e de término devem ser no momento ou depois da data atual.");
        }

        if (taskModel.getStartsAt().isAfter(taskModel.getEndsAt())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                   .body("A datas de início deve ser antes da data de término.");
        }

        var task = this.taskRepository.save(taskModel);

        return ResponseEntity.status(HttpStatus.OK).body(task);
    }

    @GetMapping("/")
    public List<TaskModel> list(HttpServletRequest request) {
        var tasks = this.taskRepository.findByIdUser((UUID) request.getAttribute("idUser"));

        return tasks;
    }

    @PutMapping("/{idTask}")
    public ResponseEntity update(@RequestBody TaskModel taskModel, HttpServletRequest request, @PathVariable UUID idTask) {
        var task = this.taskRepository.findById(idTask).orElse(null);

        if (task == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                   .body("Tarefa não encontrada.");
        }

        var idUser = request.getAttribute("idUser");
        if (!task.getIdUser().equals(idUser)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                   .body("Usuário não tem permissão para alterar essa tarefa.");
        }

        Utils.copyNonNullProperties(taskModel, task); // Copia propriedades não nulas de taskModel para task;
        
        return ResponseEntity.ok().body(this.taskRepository.save(task));
    }

    
}
