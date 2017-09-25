package samples.springbootApp.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import samples.springbootApp.dto.Response;
import samples.springbootApp.entity.Task;
import samples.springbootApp.service.TaskService;

@RestController
public class TaskController {

	@Autowired
	TaskService taskService;

	@RequestMapping(method = RequestMethod.POST, value = "/addOrUpdateTask", produces = "application/json")
	public Response addOrUpdateTask(@RequestBody Task task) throws Exception {

		Response response = taskService.addOrUpdateTask(task);

		return response;
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/deleteTask/{taskId}", produces = "application/json")
	public Response deleteTask(@PathVariable  Integer taskId) throws Exception {

		Response response = taskService.deleteTask(taskId);

		return response;
	}

	@ExceptionHandler
	void handleException(Exception e, HttpServletResponse response) throws Exception {

		response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value());

	}
}
