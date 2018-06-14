/*
 * Copyright 2012-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.AlexanderParhomov.testkafkademo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.Entity;
import java.util.Objects;

public class SampleMessage {

	private final Integer id;

	private final String message;

	@JsonIgnore
	private transient final ObjectMapper mapper;


	@JsonCreator
	public SampleMessage(@JsonProperty("id") Integer id,
						 @JsonProperty("message") String message) {
		this.id = id;
		this.message = message;
		this.mapper = new ObjectMapper();
	}

	public Integer getId() {
		return this.id;
	}

	public String getMessage() {
		return this.message;
	}

	public String toJson() {
		try {
			return mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String toString() {
		return "SampleMessage{id=" + this.id + ", message='" + this.message + "'}";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		SampleMessage that = (SampleMessage) o;
		return Objects.equals(id, that.id) &&
				Objects.equals(message, that.message);
	}

	@Override
	public int hashCode() {

		return Objects.hash(id, message);
	}
}
