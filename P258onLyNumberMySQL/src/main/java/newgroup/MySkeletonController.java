//コントローラー。マッピングなどのアプリケーション制御を行う

package newgroup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import newgroup.entity.MyDataEntity;
import newgroup.repository.MyDataRepository;

@Controller
public class MySkeletonController {

	@Autowired
	MyDataRepository repository;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView index(
		@ModelAttribute("formModel") MyDataEntity mydata,
			ModelAndView mav) {
		mav.setViewName("index");
		mav.addObject("msg","this is sample content.");
		mav.addObject("formModel",mydata);
		Iterable<MyDataEntity> list = repository.findAll();
		mav.addObject("datalist",list);
		return mav;
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	@Transactional(readOnly=false)
	public ModelAndView form(
			@ModelAttribute("formModel")
			@Validated MyDataEntity mydata,
			BindingResult result,
			ModelAndView mov) {
		ModelAndView res = null;
		if (!result.hasErrors()){
			repository.saveAndFlush(mydata);
			res = new ModelAndView("redirect:/");
		} else {
			mov.setViewName("index");
			mov.addObject("msg","sorry, error is occured...");
			Iterable<MyDataEntity> list = repository.findAll();
			mov.addObject("datalist",list);
			res = mov;
		}
		return res;
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public ModelAndView edit(@ModelAttribute MyDataEntity mydata,
			@PathVariable int id,ModelAndView mav) {
		mav.setViewName("edit");
		mav.addObject("title","edit mydata.");
		MyDataEntity data = repository.findById((long)id);
		mav.addObject("formModel",data);
		return mav;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@Transactional(readOnly=false)
	public ModelAndView update(@ModelAttribute MyDataEntity mydata,
			ModelAndView mav) {
		repository.saveAndFlush(mydata);
		return new ModelAndView("redirect:/");
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public ModelAndView delete(@PathVariable int id,
			ModelAndView mav) {
		mav.setViewName("delete");
		mav.addObject("title","delete mydata.");
		MyDataEntity data = repository.findById((long)id);
		mav.addObject("formModel",data);
		return mav;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@Transactional(readOnly=false)
	public ModelAndView remove(@RequestParam long id,
			ModelAndView mav) {
		repository.delete(id);
		return new ModelAndView("redirect:/");
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	  public String login(Model model) {
	    return "security/login";
	  }

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@Transactional(readOnly=false)
	public ModelAndView remove(	ModelAndView mav) {
		return new ModelAndView("redirect:/");
	}

	@RequestMapping(value = "/nosecurity", method = RequestMethod.GET)
	  public String nosecurity(Model model) {
	    return "security/nosecurity";
	  }

	@RequestMapping(value = "/admin/index", method = RequestMethod.GET)
	  public String adminspage(Model model) {
	    return "adminsindex";
	  }
	/*
	@PostConstruct
	//初期化（コンストラクト）を行う。mysqlに追加され続けて邪魔なので削除。メモリにデータを保存するタイプならコメントアウトははずすべき
	public void init(){
		MyDataEntity d1 = new MyDataEntity();
		d1.setName("tuyano");
		d1.setAge(123);
		d1.setMail("syoda@tuyano.com");
		d1.setMemo("090999999");
		repository.saveAndFlush(d1);
		MyDataEntity d2 = new MyDataEntity();
		d2.setName("hanako");
		d2.setAge(15);
		d2.setMail("hanako@flower");
		d2.setMemo("080888888");
		repository.saveAndFlush(d2);
		MyDataEntity d3 = new MyDataEntity();
		d3.setName("sachiko");
		d3.setAge(37);
		d3.setMail("sachico@happy");
		d3.setMemo("070777777");
		repository.saveAndFlush(d3);
	}*/


}