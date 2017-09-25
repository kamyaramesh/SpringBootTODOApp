/**
 * 
 */
package samples.springbootApp.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import samples.springbootApp.Constants.Messages;
import samples.springbootApp.Constants.Status;
import samples.springbootApp.dto.Response;
import samples.springbootApp.entity.Task;
import samples.springbootApp.util.TaksJSONObject;

@Service
public class TaskService {
	
	private static final String fileName = "src/main/resources/dataFile.json";
	
	public Response addOrUpdateTask(Task task) throws Exception {

		Response response = null;
		if(task == null){
			response = this.setErrorResponse(Status.INVALID_REQUEST);
			response.setErrorMessage(Messages.TASK_CANNOT_BE_NULL);
		}
		if(task.getName() == null || task.getName().isEmpty()){
			response = this.setErrorResponse(Status.INVALID_REQUEST);
			response.setErrorMessage(Messages.TASK_NAME_CANNOT_BE_EMPTY);
		}
		
		Gson gson = new Gson();
		TaksJSONObject existingTasks = this.getExistingTasks();
		String tasksAsJSON = null;
		
		
		if (existingTasks != null && existingTasks.getTasks() != null && !existingTasks.getTasks().isEmpty()) {
			Task found = existingTasks.getTasks().stream().filter(t -> t.getId() == task.getId()).findAny().orElse(null);

			if(found == null){ // add task
				existingTasks.getTasks().add(task);
				tasksAsJSON = gson.toJson(existingTasks);
			}else{ //update
				for(Task t : existingTasks.getTasks()){
					if(t.getId() == task.getId()){
						t.copyContentsFrom(task);
						break;
					}
				}
				tasksAsJSON = gson.toJson(existingTasks);
			}
		}else{ // add task
			TaksJSONObject newTask = new TaksJSONObject();
			List<Task> list = new ArrayList<Task>();
			list.add(task);
			newTask.setTasks(list);
			tasksAsJSON = gson.toJson(newTask);
		}

		Files.write(Paths.get(fileName), tasksAsJSON.getBytes());
		response = setSuccessResponse();
		response.setMessage(Messages.TASK_ADDED_SUCCESSFULLY);
		return response;
		
	}
	

	public Response deleteTask(Integer taskId) throws IOException{
		Response response = null;
		if(taskId == null){
			response = this.setErrorResponse(Status.INVALID_REQUEST);
			response.setErrorMessage(Messages.TASK_CANNOT_BE_NULL);
		}

		
		Gson gson = new Gson();
		TaksJSONObject existingTasks = this.getExistingTasks();

		if (existingTasks != null && existingTasks.getTasks() != null && !existingTasks.getTasks().isEmpty()) {
			Task found = existingTasks.getTasks().stream().filter(t -> t.getId() == taskId).findAny().orElse(null);
		
			if(found == null){
				response = this.setErrorResponse(Status.INVALID_REQUEST);
				response.setErrorMessage(Messages.TASK_DOES_NOT_EXIST);
			}else{
				existingTasks.getTasks().remove(found);
				String tasksAsJSON = gson.toJson(existingTasks);
				Files.write(Paths.get(fileName), tasksAsJSON.getBytes());
				response = setSuccessResponse();
				response.setMessage(Messages.TASK_ADDED_SUCCESSFULLY);
			}
		}else {
			response = this.setErrorResponse(Status.INVALID_REQUEST);
			response.setErrorMessage(Messages.TASK_DOES_NOT_EXIST);

		}

		return response;

	}
	
	private Response setSuccessResponse(){
		Response response = new Response();
		response.setStatus(Status.SUCCESS.name());
		response.setStatusCode(Status.SUCCESS.getValue());
		return response;
	}
	
	private Response setErrorResponse(Status status){
		Response response = new Response();
		response.setStatus(status.name());
		response.setStatusCode(status.getValue());
		return response;
	}
	
	private TaksJSONObject getExistingTasks() throws IOException {

		
		StringBuilder sb = new StringBuilder();
		Gson gson = new Gson();
		TaksJSONObject existingTasks = null;

		Files.lines(Paths.get(fileName)).forEach(string -> sb.append(string));

		if (sb.length() > 0)  return existingTasks = gson.fromJson(sb.toString(), TaksJSONObject.class);
		else return null;
		
	}

}
