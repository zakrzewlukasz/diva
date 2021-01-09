package org.springframework.diva.app.object;

import org.springframework.diva.app.file.FileStream;
import org.springframework.diva.app.model.ObjectFeatures;
import org.springframework.diva.app.model.ObjectRq;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;

@Controller
@Service
public class ObjectController {


	private static final String SEND_OBJECT_FORM = "objects/sendObjectForm";

	private final ObjectRqRepository objectsRq;
	private final ObjectRepository objects;


	public ObjectController(ObjectRqRepository objectRqService, ObjectRepository objectService) {
		this.objectsRq = objectRqService;
		this.objects = objectService;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping("/objects/new")
	public String initCreationForm(Map<String, Object> model) {
		ObjectRq objectRq = new ObjectRq();
		model.put("objectRq", objectRq);
		return SEND_OBJECT_FORM;
	}

	@PostMapping("/objects/new")
	public String processCreationForm(@Valid ObjectRq objectRq, BindingResult result)  {
		if (result.hasErrors()) {
			System.out.println(objectRq);
			return SEND_OBJECT_FORM;
		}
		else {
			System.out.println(objectRq);
			this.objectsRq.save(objectRq);
			return "redirect:/objects/" + objectRq.getId();
		}
	}

	@GetMapping("/objects/{objectId}")
	public ModelAndView showObjectsRq(@PathVariable("objectId") int objectId) {
		ModelAndView mav = new ModelAndView("objects/objectDetails");
		ObjectRq objectRq = this.objectsRq.findById(objectId);
		mav.addObject(objectRq);
		return mav;
	}

	@GetMapping("/objects/find")
	public String initFindForm(Map<String, Object> model) throws IOException {
/*		ObjectFeatures objectFeatures = new ObjectFeatures();
		model.put("objectFeatures", objectFeatures);
		FileStream fileStream = new FileStream();
		fileStream.listFilesUsingDirectoryStream("M:/MASTERY/Dla_Marka");*/
		/*return "objects/findObjects";*/
		return "tree_view/tree_tes";
	}



/*@GetMapping("/objects")
	public String processFindForm(ObjectRq objectRq, BindingResult result, Map<String, Object> model) {

		// allow parameterless GET request for /owners to return all records
		if (objectRq.getLastName() == null) {
			objectRq.setLastName(""); // empty string signifies broadest possible search
		}


		// find owners by last name
		Collection<ObjectRq> results = this.objectsRq.findByLastName(objectRq.getNameObj());
		if (results.isEmpty()) {
			// no owners found
			result.rejectValue("lastName", "notFound", "not found");
			return "owners/findOwners";
		}
		else if (results.size() == 1) {
			// 1 owner found
			objectRq = results.iterator().next();
			return "redirect:/owners/" + objectRq.getId();
		}
		else {
			// multiple owners found
			model.put("selections", results);
			return "owners/ownersList";
		}
	}*/





}


