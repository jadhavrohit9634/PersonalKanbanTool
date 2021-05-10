import {GET_PROJECTS} from '../actions/types';

const initialState = {
    projects: [],
    project: {}
};

const projectRedcuer = (state = initialState, action) => {
    switch(action.type){
        
        case GET_PROJECTS:
            return {
                ...state,
                projects: action.payload
            }
        default: 
            return state;
    }
}
export default projectRedcuer;