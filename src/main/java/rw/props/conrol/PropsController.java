package rw.props.conrol;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import rw.props.domain.DataTablesResponse;
import rw.props.domain.MyEntry;
import rw.props.service.PropsService;
import flexjson.JSONSerializer;

@RequestMapping(value="/tables")
@Controller
public class PropsController {
	
	@Resource(name="propsService")
	private PropsService<MyEntry> propsService;

	
	@PostConstruct
	public void init() {
			//DO NOTHING
	}
	@RequestMapping(value="/delete")
	public String delete(ModelMap model,@Valid MyEntry myEntry, BindingResult result) throws Exception {
		propsService.delete(myEntry);
		if (result.hasErrors()) {
			return "redirect:editing";
		}
		return "redirect:/";
	}
	
	@RequestMapping(value="/create")
	public String create(ModelMap model,@Valid MyEntry myEntry, BindingResult result) throws Exception {
		propsService.add(myEntry);
		if (result.hasErrors()) {
			return "redirect:editing";
		}
		return "redirect:/";
	}
	
	@RequestMapping(value="/update")
	public String update(ModelMap model,@Valid MyEntry myEntry, BindingResult result) throws Exception {
		propsService.update(myEntry);
		if (result.hasErrors()) {
			return "redirect:editing";
		}
		return "redirect:/";
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String delete(Model model,@RequestParam(required = true)String key,@RequestParam(required = true)String value) throws Exception {
//			MyEntry myEntry= propsService.getEntryByKey(key);
			MyEntry myEntry = new MyEntry(key,value);
			model.addAttribute("myEntry",myEntry);
		
		return "property/update";
	}
	
	@RequestMapping(method = RequestMethod.GET,params = "form")
	public String editing(Model model) {
		MyEntry myEntry = new MyEntry();
		myEntry.setKey("Ключ");
		myEntry.setValue("Значение");
		model.addAttribute("myEntry", myEntry);
		return "property/create";
		
	}
	
	@RequestMapping(params = "data", method = RequestMethod.POST, headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> data(@RequestBody String json) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        
        try{
        	DataTablesResponse<MyEntry> result = propsService.getPropsListResponse(json);
        	return  new ResponseEntity<String>(new JSONSerializer()
			        	.include("aaData.key")
			        	.include("aaData.value")
			        	.exclude("aaData.*")
		        		.exclude("*.class")
		        		.deepSerialize(result), headers, HttpStatus.OK);
        } catch (Exception ex) {
        	ex.printStackTrace();
        	return new ResponseEntity<String>(ex.toString(), headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
