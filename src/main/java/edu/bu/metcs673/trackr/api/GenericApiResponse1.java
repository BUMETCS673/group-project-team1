package edu.bu.metcs673.trackr.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import edu.bu.metcs673.trackr.common.CommonConstants;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * Simple object used to contain success and error responses to return
 * meaningful information back to the user.
 * # The purpose of Changing GenericApiResponse to GenericApiResponse1 is to be convenient for
 * # retrieve data from the response data without losing data types #
 *
 * @author Xiaobing Hou
 * @date 05/25/2022
 */
@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GenericApiResponse1<G> {

    @NotNull
    private String status;
    @NotNull
    private LocalDate date;
    @NotNull
    private String message;

    @JsonInclude(Include.NON_EMPTY)
    private G additionalData;

    /**
     * Factory method to create a successful response with the provided message.
     * Optional object parameter to include returned objects
     *
     * @param message
     * @return
     */
    public static <T> GenericApiResponse1<T> successResponse(String message, T t) {
        return new GenericApiResponse1<>(CommonConstants.SUCCESS, LocalDate.now(), message, t);
    }

    /**
     * Factory method to create an error response with the provided message.
     * Optional object parameter to include returned objects
     *
     * @param message
     * @return
     */
    public static <T> GenericApiResponse1<T> errorResponse(String message, T t) {
        return new GenericApiResponse1<>(CommonConstants.ERROR, LocalDate.now(), message, t);
    }
}