package samples.springbootApp.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.boot.test.context.SpringBootTest;

import samples.springbootApp.Constants.Status;
import samples.springbootApp.dto.Response;
import samples.springbootApp.entity.Task;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskServiceTest {
	
	@Autowired
	TaskService taskService;
	
	@Test
	public void testAddTask() throws Exception{
		try{
		Response response = taskService.addOrUpdateTask(this.createTask());
		if(response != null){
			if(response.getStatus() != Status.SUCCESS.name()){
				Assert.fail("Unexpected status");
			}
		}else{
			Assert.fail("response was null");
		}

		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void testDeleteTask() throws Exception{
		try{
		Response response = taskService.deleteTask(2);
		if(response != null){
			if(response.getStatus() != Status.SUCCESS.name()){
				Assert.fail("Unexpected status");
			}
		}else{
			Assert.fail("response was null");
		}

		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	

	private Task createTask(){
		Task task = new Task();
		task.setId(2);
		task.setName("Prep for interview");
		return task;
	}
}
