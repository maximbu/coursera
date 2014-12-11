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
package org.magnum.dataup;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.magnum.dataup.model.Video;
import org.magnum.dataup.model.VideoStatus;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
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

@Controller
public class VideoController {

	/**
	 * You will need to create one or more Spring controllers to fulfill the
	 * requirements of the assignment. If you use this file, please rename it to
	 * something other than "AnEmptyController"
	 * 
	 * 
	 * ________ ________ ________ ________ ___ ___ ___ ________ ___ __ |\
	 * ____\|\ __ \|\ __ \|\ ___ \ |\ \ |\ \|\ \|\ ____\|\ \|\ \ \ \ \___|\ \
	 * \|\ \ \ \|\ \ \ \_|\ \ \ \ \ \ \ \\\ \ \ \___|\ \ \/ /|_ \ \ \ __\ \ \\\
	 * \ \ \\\ \ \ \ \\ \ \ \ \ \ \ \\\ \ \ \ \ \ ___ \ \ \ \|\ \ \ \\\ \ \ \\\
	 * \ \ \_\\ \ \ \ \____\ \ \\\ \ \ \____\ \ \\ \ \ \ \_______\ \_______\
	 * \_______\ \_______\ \ \_______\ \_______\ \_______\ \__\\ \__\
	 * \|_______|\|_______|\|_______|\|_______|
	 * \|_______|\|_______|\|_______|\|__| \|__|
	 * 
	 * 
	 */

	private Map<Long, Video> videos = new HashMap<Long, Video>();
	 private static final AtomicLong currentId = new AtomicLong(0L);
	

	@RequestMapping(value = "/video/{id}/data", method = RequestMethod.POST)
	public @ResponseBody
	VideoStatus setVideoData(@PathVariable("id") long id,
			@RequestParam("data") MultipartFile videoData) throws IOException {
		try {
			Video v= videos.get(id);
			VideoFileManager.get().saveVideoData(v, videoData.getInputStream());
			return new VideoStatus(VideoStatus.VideoState.READY);
		} catch (NullPointerException ne) {
			throw new ResourceNotFoundException("ID does not exist: " + id);
		}
	}
	
	@RequestMapping(value = "/video/{id}/data", method = RequestMethod.GET)
	public @ResponseBody
	HttpServletResponse getVideoData(@PathVariable("id") long id , HttpServletResponse response) throws IOException {
		try {
			Video v= videos.get(id);
			
			ServletOutputStream out = response.getOutputStream();
			VideoFileManager.get().copyVideoData(v, response.getOutputStream());			
			return response;
		} catch (NullPointerException ne) {
			throw new ResourceNotFoundException("ID does not exist: " + id);
		}
	}

	@RequestMapping(value = "/video", method = RequestMethod.POST)
	public @ResponseBody
	Video addVideo(@RequestBody Video v) {
		long id = currentId.incrementAndGet();		
		v.setId(id);
		v.setDataUrl(getDataUrl(id));
		videos.put(id, v);
		return v;
	}

	@RequestMapping(value = "/video", method = RequestMethod.GET)
	public @ResponseBody
	Collection<Video> getVideoList() {
		return videos.values();
	}
	
	private String getDataUrl(long videoId){
        String url = getUrlBaseForLocalServer() + "/video/" + videoId + "/data";
        return url;
    }

    private String getUrlBaseForLocalServer() {
       HttpServletRequest request = 
           ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
       String base = 
          "http://"+request.getServerName() 
          + ((request.getServerPort() != 80) ? ":"+request.getServerPort() : "");
       return base;
    }

}
