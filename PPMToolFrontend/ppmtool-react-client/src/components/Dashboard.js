import React, { Component } from 'react'
import ProjectItem from './Project/ProjectItem';

class Dashboard extends Component {
    render() {
        return (
            <main>
                <header>
                    <h1>Welcome to the Dashboard.</h1>
                </header>           
                <ProjectItem />
            </main>
            
        );
    }
}

export default Dashboard;
