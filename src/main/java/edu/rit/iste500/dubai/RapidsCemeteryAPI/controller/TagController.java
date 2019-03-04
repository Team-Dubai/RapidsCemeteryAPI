package edu.rit.iste500.dubai.RapidsCemeteryAPI.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.rit.iste500.dubai.RapidsCemeteryAPI.enums.ExceptionEnum;
import edu.rit.iste500.dubai.RapidsCemeteryAPI.exception.APIException;
import edu.rit.iste500.dubai.RapidsCemeteryAPI.model.Tag;
import edu.rit.iste500.dubai.RapidsCemeteryAPI.request.TagRequest;
import edu.rit.iste500.dubai.RapidsCemeteryAPI.service.TagService;

@RestController
@RequestMapping(value = "/api/tag", produces = MediaType.APPLICATION_JSON_VALUE)
public class TagController {

	@Autowired
	private TagService tagService;

	@CrossOrigin(origins = { "*" })
	@RequestMapping(value = "/getAllTags", method = RequestMethod.GET, produces = { "application/json" })
	public List<Tag> getAllTags(HttpServletRequest request, HttpServletResponse response) {

		List<Tag> tags = null;

		try {
			tags = tagService.fetchAllTags();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return tags;
	}

	@CrossOrigin(origins = { "*" })
	@RequestMapping(value = "/saveTag", method = RequestMethod.POST, produces = { "application/json" })
	public Tag saveTag(@RequestBody TagRequest tagRequest, HttpServletRequest request, HttpServletResponse response) {

		Tag tag = null;
		try {
			validateSaveTagRequest(tagRequest);
			tag = tagService.save(tagRequest.getTag());

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return tag;
	}

	@CrossOrigin(origins = { "*" })
	@RequestMapping(value = "/getTagById", method = RequestMethod.POST, produces = { "application/json" })
	public Tag getTagById(@RequestBody TagRequest tagRequest, HttpServletRequest request,
			HttpServletResponse response) {

		Tag tag = null;
		try {
			if (tagRequest.getId() > 0) {
				tag = tagService.loadTagById(tagRequest.getId());
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return tag;
	}

	@CrossOrigin(origins = { "*" })
	@RequestMapping(value = "/removeTagById", method = RequestMethod.POST, produces = { "application/json" })
	public boolean removeTagById(@RequestBody TagRequest tagRequest, HttpServletRequest request,
			HttpServletResponse response) {

		try {
			if (tagRequest.getId() > 0) {
				tagService.removeTagById(tagRequest.getId());
				return true;

			}

		} catch (Exception ex) {
			ex.printStackTrace();
			return false;

		}

		return false;

	}

	private void validateSaveTagRequest(TagRequest request) throws APIException {
		if (request.getTag() == null) {
			throw new APIException(ExceptionEnum.BAD_REQUEST, null);
		}
	}

}
