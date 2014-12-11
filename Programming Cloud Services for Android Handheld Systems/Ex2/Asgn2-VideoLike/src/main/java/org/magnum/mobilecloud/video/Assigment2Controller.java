/*
 * 
 * Copyright 2014 Jules White
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */

package org.magnum.mobilecloud.video;

import java.io.IOException;
import java.security.Principal;
import java.util.Collection;
import java.util.Set;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.magnum.mobilecloud.video.repository.Video;
import org.magnum.mobilecloud.video.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;

@Controller
public class Assigment2Controller {
	
	/**
	 * You will need to create one or more Spring controllers to fulfill the
	 * requirements of the assignment. If you use this file, please rename it
	 * to something other than "AnEmptyController"
	 * 
	 * 
		 ________  ________  ________  ________          ___       ___  ___  ________  ___  __       
		|\   ____\|\   __  \|\   __  \|\   ___ \        |\  \     |\  \|\  \|\   ____\|\  \|\  \     
		\ \  \___|\ \  \|\  \ \  \|\  \ \  \_|\ \       \ \  \    \ \  \\\  \ \  \___|\ \  \/  /|_   
		 \ \  \  __\ \  \\\  \ \  \\\  \ \  \ \\ \       \ \  \    \ \  \\\  \ \  \    \ \   ___  \  
		  \ \  \|\  \ \  \\\  \ \  \\\  \ \  \_\\ \       \ \  \____\ \  \\\  \ \  \____\ \  \\ \  \ 
		   \ \_______\ \_______\ \_______\ \_______\       \ \_______\ \_______\ \_______\ \__\\ \__\
		    \|_______|\|_______|\|_______|\|_______|        \|_______|\|_______|\|_______|\|__| \|__|
                                                                                                                                                                                                                                                                        
	 * 
	 */
	@Autowired
	private VideoRepository videoRepo ;
	
	
	@RequestMapping(value="/go",method=RequestMethod.GET)
	public @ResponseBody String goodLuck(){
		return "Good Luck!";
	}
	
	
	
	@RequestMapping(value = "/video/{id}", method = RequestMethod.GET)
	public @ResponseBody
	Video getVideo(@PathVariable("id") long id){
		return videoRepo.findOne(id);
	}
	
	@RequestMapping(value = "/video/search/findByName", method = RequestMethod.GET)
	public @ResponseBody
	Collection<Video> getVideoByName(@RequestParam("title") String title){
		return videoRepo.findByName(title);
	}
	
	@RequestMapping(value = "/video/search/findByDurationLessThan", method = RequestMethod.GET)
	public @ResponseBody
	Collection<Video> getVideoByDurationLessThan(@RequestParam("duration") long duration){
		return videoRepo.findByDurationLessThan(duration);
	}

	@RequestMapping(value = "/video", method = RequestMethod.POST)
	public @ResponseBody
	Video addVideo(@RequestBody Video v) {
		v.setLikes(0);
		videoRepo.save(v);
		return v;
	}

	@RequestMapping(value = "/video", method = RequestMethod.GET)
	public @ResponseBody
	Collection<Video> getVideoList() {
		return Lists.newArrayList(videoRepo.findAll());
	}

	@RequestMapping(value = "/video/{id}/like", method = RequestMethod.POST)
	public @ResponseBody
	ResponseEntity<Void> setVideoLike(@PathVariable("id") long id , Principal p){
		String userName = p.getName();
		Video v = videoRepo.findOne(id);
		if(v == null)
		{
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		Set<String> likes = v.getLikesUsernames();
		if(likes.contains(userName))
		{
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
		likes.add(userName);	
		v.setLikesUsernames(likes);
		v.setLikes(likes.size());
		videoRepo.save(v);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/video/{id}/unlike", method = RequestMethod.POST)
	public @ResponseBody
	ResponseEntity<Void> setVideoUnLike(@PathVariable("id") long id , Principal p){
		String userName = p.getName();
		Video v = videoRepo.findOne(id);
		if(v == null)
		{
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		Set<String> likes = v.getLikesUsernames();
		if(!likes.contains(userName))
		{
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
		likes.remove(userName);	
		v.setLikesUsernames(likes);
		v.setLikes(likes.size());
		videoRepo.save(v);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/video/{id}/likedby", method = RequestMethod.GET)
	public @ResponseBody
	ResponseEntity<Collection<String>> getVideoLikedBy(@PathVariable("id") long id){
		Video v = videoRepo.findOne(id);
		if(v == null)
		{
			return new ResponseEntity<Collection<String>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Collection<String>>(v.getLikesUsernames(),HttpStatus.OK);
	}
	
}
