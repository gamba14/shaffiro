/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ShaffiroTestModule } from '../../../test.module';
import { DispositivoDeleteDialogComponent } from 'app/entities/dispositivo/dispositivo-delete-dialog.component';
import { DispositivoService } from 'app/entities/dispositivo/dispositivo.service';

describe('Component Tests', () => {
    describe('Dispositivo Management Delete Component', () => {
        let comp: DispositivoDeleteDialogComponent;
        let fixture: ComponentFixture<DispositivoDeleteDialogComponent>;
        let service: DispositivoService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ShaffiroTestModule],
                declarations: [DispositivoDeleteDialogComponent]
            })
                .overrideTemplate(DispositivoDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DispositivoDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DispositivoService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
