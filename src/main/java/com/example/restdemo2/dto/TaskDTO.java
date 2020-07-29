package com.example.restdemo2.dto;
import com.example.restdemo2.domain.Task;
import lombok.Data;
import org.modelmapper.ModelMapper;

@Data
public class TaskDTO {
    private static ModelMapper modelMapper = new ModelMapper();

    private String title;
    private String description;
    private String image;
    private Long personId;

    public TaskDTO(Task task) {
        modelMapper.map(task, this);
    }
}
