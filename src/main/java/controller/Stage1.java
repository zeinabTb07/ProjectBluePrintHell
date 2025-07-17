package controller;

import model.*;

public class Stage1 extends Stage{
    public Stage1( int wireLength, int stageTime) {
        super( wireLength, stageTime);
        G_System system0 = new G_System(50 , 120);
        ReferenceSystem referenceSystem = new ReferenceSystem( 200 , 220);
        G_System system1  = new G_System(300 , 350);

        system0.addInputPort(new InputPort(system0 , Type.Triangle));
        system0.addInputPort(new InputPort(system0 , Type.Rectangle));
        system0.addOutputPort(new OutputPort(system0 , Type.Triangle));

        system1.addInputPort(new InputPort(system1 , Type.Triangle));
        system1.addInputPort(new InputPort(system1 , Type.Rectangle));
        system1.addOutputPort(new OutputPort(system1 , Type.Triangle));
        system1.addOutputPort(new OutputPort(system1 , Type.Triangle));

        referenceSystem.addRectPacket();
        referenceSystem.addTriPacket();
        referenceSystem.addOutputPort(new OutputPort(referenceSystem , Type.Triangle));
        referenceSystem.addInputPort(new InputPort(referenceSystem , Type.Triangle));

        this.addSystem(system0);
        this.addSystem(system1);
        this.addSystem(referenceSystem);

        G_System system2 = new G_System(400 , 500);
        system2.addOutputPort(new OutputPort(system2 , Type.Triangle));
        system2.addOutputPort(new OutputPort(system2 , Type.Rectangle));
        system2.addInputPort(new InputPort(system2 , Type.Triangle));
        this.addSystem(system2);

    }
}
