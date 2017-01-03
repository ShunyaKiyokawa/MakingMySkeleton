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
	//final String ROLE_ADMIN = "ROLE_ADMIN"; のちのち定数化した方が修正楽そう
	public enum Authority {ROLE_USER, ROLE_ADMIN};

	@Autowired
	MyDataRepository repository;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView index(
		@ModelAttribute("formModel") MyDataEntity mydata,
			ModelAndView mav) {
		mav.setViewName("index");
		mav.addObject("msg","this is sample content.");
		mav.addObject("role_admin","ROLE_ADMIN"); //管理者権限のロールを指定する
		mav.addObject("role_user","ROLE_USER"); //一般ユーザーロールを指定する。このページで作成するのは一般ユーザー
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
		mov.addObject("role_admin","ROLE_ADMIN"); //管理者権限のロールを指定する
		mov.addObject("role_user","ROLE_USER"); //一般ユーザーロールを指定する。このページで作成するのは一般ユーザー

/*       // パスワードを暗号化する
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String passwordHash = encoder.encode(password.getPasswordHash());
        // System.out.println("エンコード前:\t" + account.getPasswordHash());
        account.setPasswordHash(passwordHash);
        // System.out.println("エンコード後:\t" + account.getPasswordHash());
        accountService.saveAccount(account);*/

		return res;
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public ModelAndView edit(@ModelAttribute MyDataEntity mydata,
			@PathVariable int id,ModelAndView mav) {
		mav.setViewName("edit");
		mav.addObject("title","edit mydata.");
		MyDataEntity data = repository.findById((long)id);
		mav.addObject("formModel",data);
		mav.addObject("role_user","ROLE_USER");
		return mav;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@Transactional(readOnly=false)
	public ModelAndView update(@ModelAttribute MyDataEntity mydata,
			ModelAndView mav) {
		repository.saveAndFlush(mydata);
		mav.addObject("role_user","ROLE_USER");
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
//modelはtemplateのhtml情報がないのでそのままreturnできない。returnしたかったらModelAndViewを使う。
//ちなみにﾘﾀｰﾝで指定しているのはhtmlのファイル構造（templatesフォルダ下から）
	@RequestMapping(value = "/admin/index", method = RequestMethod.GET)
	public ModelAndView adminindex(
		@ModelAttribute("formModel") MyDataEntity mydata,
			ModelAndView mav) {
		mav.setViewName("adminsindex");
		mav.addObject("msg","this is sample content.");
		mav.addObject("role_admin","ROLE_ADMIN"); //管理者権限のロールを指定する
		mav.addObject("role_user","ROLE_USER"); //一般ユーザーロールを指定する。このページで作成するのは一般ユーザー
		mav.addObject("formModel",mydata);
		Iterable<MyDataEntity> list = repository.findAll();
		mav.addObject("datalist",list);
		return mav;
	}
	@RequestMapping(value = "/admin/index", method = RequestMethod.POST)
	@Transactional(readOnly=false)
	public ModelAndView adminform(
			@ModelAttribute("formModel")
			@Validated MyDataEntity mydata,
			BindingResult result,
			ModelAndView mov) {
		ModelAndView res = null;
		if (!result.hasErrors()){
			repository.saveAndFlush(mydata);
			res = new ModelAndView("redirect:/");
		} else {
			mov.setViewName("adminsindex");
			mov.addObject("msg","sorry, error is occured...");
			Iterable<MyDataEntity> list = repository.findAll();
			mov.addObject("datalist",list);
			res = mov;
		}
		mov.addObject("role_admin","ROLE_ADMIN"); //管理者権限のロールを指定する
		mov.addObject("role_user","ROLE_USER"); //一般ユーザーロールを指定する。このページで作成するのは一般ユーザー
		return res;
	}

/*
	@PostConstruct
	//初期化（コンストラクト）を行う。mysqlに追加され続けて邪魔なので削除。メモリにデータを保存するタイプならコメントアウトははずすべき。ちなみにこれ使うと現在エラーが発生中
	public void init(){
		MyDataEntity d1 = new MyDataEntity();
		d1.setUsername("tuyano");
		d1.setAge(10);
		d1.setRole("ROLE_ADMIN");
		d1.setPassword("password");
		d1.setMail("ore@oreeore");
		d1.setMemo("090999999");
		repository.saveAndFlush(d1);

	}*/


}